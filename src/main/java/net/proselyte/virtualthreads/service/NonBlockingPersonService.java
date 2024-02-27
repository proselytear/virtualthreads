package net.proselyte.virtualthreads.service;

import net.proselyte.virtualthreads.dto.PersonRequestDto;
import net.proselyte.virtualthreads.remote.RemotePersonService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class NonBlockingPersonService {

    private final RemotePersonService remotePersonService = new RemotePersonService();
    private final EmailService emailService = new EmailService();
    private final ActionLogService actionLogService = new ActionLogService();

    public CompletableFuture<Boolean> sendEmailToUserByUid(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> remotePersonService.getPerson(buildPersonRequest(uuid)))
                .thenApply(personDto -> emailService.sendEmail(personDto.email()))
                .thenCompose(email -> CompletableFuture.supplyAsync(() -> actionLogService.saveEmailSentAction(email)));
    }

    private PersonRequestDto buildPersonRequest(UUID uid) {
        return new PersonRequestDto(uid.toString());
    }
}
