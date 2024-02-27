package net.proselyte.virtualthreads;
import net.proselyte.virtualthreads.service.NonBlockingPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingAppRunner {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int numberOfRequests = 10000;
        AtomicInteger sendEmailsCounter = new AtomicInteger(0);
        List<UUID> uuids = generateRandomUUIDs(numberOfRequests);

        NonBlockingPersonService personService = new NonBlockingPersonService();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (UUID uuid : uuids) {
            CompletableFuture<Void> future = CompletableFuture
                    .supplyAsync(() -> personService.sendEmailToUserByUid(uuid))
                    .thenAccept(result -> sendEmailsCounter.incrementAndGet());
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allOf.thenRun(() -> {
            long end = System.currentTimeMillis();
            long duration = end - start;
            System.out.println("DURATION: " + duration + " ms");
            System.out.println("SENT EMAILS: " + sendEmailsCounter);
        }).join();
    }

    private static List<UUID> generateRandomUUIDs(int numberOfUUIDs) {
        List<UUID> uuids = new ArrayList<>();
        for (int i = 0; i < numberOfUUIDs; i++) {
            uuids.add(UUID.randomUUID());
        }
        return uuids;
    }
}
