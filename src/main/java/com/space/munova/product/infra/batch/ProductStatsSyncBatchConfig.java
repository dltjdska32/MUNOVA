package com.space.munova.product.infra.batch;

import com.space.munova.product.infra.batch.dto.ProductStatsSyncDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ThreadPoolExecutor;

///  1시간마다 좋아요, 판매량, 조회수를 각각의 상품별로 레디스에 저장해놓은 값을 가져와
/// 몽고, RDB, ES에 업데이트.
@Configuration
@RequiredArgsConstructor
public class ProductStatsSyncBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final ProductStatsReader productStatsReader;
    private final ProductStatsProcessor productStatsProcessor;
    private final ProductStatsWriter productStatsWriter;



    /// 스레드 2개 생성 -> 비동기로 쓰기실행
    /// Writer 스레드가 모두 사용중일경우  메인 스레드도 쓰기에 참여

    /// 자원은 한정적이기 때문에 최소 2개로 쓰기 시작 -> 쓰기가 바쁠경우 최대 3개 까지 사용 -> 3개가 포화일 경우
    /// 메인스레드가 쓰기스레드를 기다리지 않고 쓰기작업에 동참.
    @Bean
    public TaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);  /// 사용할 스레드 갯수 2개
        executor.setMaxPoolSize(3);   /// 풀에 있는 스레드 갯수 4개
        executor.setQueueCapacity(0); /// 큐를 사용하지 않음.
        executor.setThreadNamePrefix("ProductStatsBatchTask-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());    /// 스레드 풀이 가득 찰 때 호출한 스레드가 직접 작업을 실행하도록 설정
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }


    @Bean
    public Job syncProductStatsJob() {

        return new JobBuilder("syncProductStatsJob", jobRepository)
                .start(syncProductStatsStep())
                .build();
    }


    @Bean
    public Step syncProductStatsStep() {

        return new StepBuilder("syncProductStatsStep", jobRepository)
                .<Long, ProductStatsSyncDto>chunk(5000, transactionManager) ///5천개씩 배치
                .reader(productStatsReader)
                .processor(productStatsProcessor)
                .writer(productStatsWriter)
                .taskExecutor(batchTaskExecutor())
                .build();
    }

}
