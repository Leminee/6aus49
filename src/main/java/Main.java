import org.json.JSONObject;
import org.restlet.resource.ClientResource;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

        TreeSet<Integer> lottoNumbers = generateLottoNumbers();

        final String bRockYouApiUrl = "https://raw.githubusercontent.com/JohannesFriedrich/LottoNumberArchive/master/Lottonumbers_complete.json";
        final ClientResource resource = new ClientResource(bRockYouApiUrl);

        try {
            final String apiResponseContent = resource.get().getText();
            final JSONObject jsonObject = new JSONObject(apiResponseContent);
            final int amountJsonObjects =
                    jsonObject.getJSONArray("data").length();

            for (int i = 0; i < amountJsonObjects; i++) {

                final boolean alreadyExists = jsonObject
                        .getJSONArray("data")
                        .getJSONObject(i)
                        .get("Lottozahl").toString()
                        .equals(String.valueOf(lottoNumbers).replace(" ", ""));

                if (alreadyExists) {

                    lottoNumbers = generateLottoNumbers();

                    System.out.println(lottoNumbers);
                    return;
                }

            }

            System.out.println(lottoNumbers);

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }


    }


    private static TreeSet<Integer> generateLottoNumbers() {

        final TreeSet<Integer> ascendingSortedLottoNumbers = new TreeSet<>();

        final int amountRequiredNumbers = 6;

        while (ascendingSortedLottoNumbers.size() != amountRequiredNumbers) {

            final int floor = 3;
            final int upper = 45;

            ascendingSortedLottoNumbers.add(ThreadLocalRandom.current().nextInt(floor, upper + 1));

        }

        return ascendingSortedLottoNumbers;

    }

}
