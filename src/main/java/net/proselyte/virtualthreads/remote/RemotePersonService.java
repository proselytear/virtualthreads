package net.proselyte.virtualthreads.remote;

import com.google.gson.Gson;
import net.proselyte.virtualthreads.dto.PersonRequestDto;
import net.proselyte.virtualthreads.dto.PersonResponseDto;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Pseudo remote service
 */
public class RemotePersonService {

    private final String filePath = "src/main/resources/person.json";
    private final Gson gson = new Gson();

    public PersonResponseDto getPerson(PersonRequestDto requestDto) {
        try {
            Thread.sleep(1);
            PersonResponseDto personFromFile = gson.fromJson(new FileReader(filePath), PersonResponseDto.class);
            return new PersonResponseDto(
                    requestDto.uid(),
                    personFromFile.firstName(),
                    personFromFile.lastName(),
                    requestDto.uid().concat("@mail.com")
            );
        } catch (FileNotFoundException | InterruptedException e) {
            return new PersonResponseDto(
                    requestDto.uid(),
                    "John",
                    "Doe",
                    requestDto.uid().concat("@mail.com"));
        }
    }
}
