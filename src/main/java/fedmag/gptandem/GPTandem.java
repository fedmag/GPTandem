package fedmag.gptandem;
import fedmag.gptandem.services.helper.Languages;
import fedmag.gptandem.controller.Controller;
import lombok.extern.slf4j.Slf4j;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Slf4j
public class GPTandem {
    public static void main(String[] args) {

        log.info("Hello and welcome!");
        log.info("Creating empty chat history..");

        Controller controller = new Controller(Languages.ENGLISH);

//        while (true) {
//            // record if is not recording, not transcripting, not ...
//            // transcript
//            // call gpt
//            // play sound
//        }


    }
}