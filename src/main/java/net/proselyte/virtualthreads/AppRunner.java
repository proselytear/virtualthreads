package net.proselyte.virtualthreads;

import net.proselyte.virtualthreads.dto.PersonResponseDto;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppRunner {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            PersonResponseDto personResponseDto = executorService.submit(new GetPersonTask(UUID.randomUUID())).get();
            System.out.println(personResponseDto);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
