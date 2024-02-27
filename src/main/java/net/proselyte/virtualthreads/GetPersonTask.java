package net.proselyte.virtualthreads;

import net.proselyte.virtualthreads.dto.PersonRequestDto;
import net.proselyte.virtualthreads.dto.PersonResponseDto;
import net.proselyte.virtualthreads.remote.RemotePersonService;

import java.util.UUID;
import java.util.concurrent.Callable;

public class GetPersonTask implements Callable<PersonResponseDto> {
    private final RemotePersonService remotePersonService = new RemotePersonService();
    private final UUID uid;

    public GetPersonTask(UUID uuid) {
        this.uid = uuid;
    }

    @Override
    public PersonResponseDto call() {
        long buildPersonRequestStart = System.nanoTime();
        PersonRequestDto personRequestDto = buildPersonRequest(this.uid);
        long buildPersonRequestEnd = System.nanoTime();
        long buildPersonRequestDuration = buildPersonRequestEnd - buildPersonRequestStart;

        long getPersonStart = System.nanoTime();
        PersonResponseDto responseDto = remotePersonService.getPerson(personRequestDto);
        long getPersonEnd = System.nanoTime();
        long getPersonDuration = getPersonEnd - getPersonStart;

        System.out.println("buildPersonRequestDuration: " + buildPersonRequestDuration + " ns");
        System.out.println("getPersonDuration: " + getPersonDuration + " ns");
        return responseDto;
    }

    private PersonRequestDto buildPersonRequest(UUID uid) {
        return new PersonRequestDto(uid.toString());
    }
}
