//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.SheetsScopes;
//import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
//import com.google.api.services.sheets.v4.model.ValueRange;
//import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
//import com.google.api.services.sheets.v4.model.ValueInputOption;
//import com.google.api.services.sheets.v4.model.ValueRange;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.ArrayList;
//
//public class GoogleSheetsExample {
//    private static final String APPLICATION_NAME = "Your Application Name";
//    private static final String SPREADSHEET_ID = "Your Spreadsheet ID";
//    private static final String RANGE = "Sheet1!A:B"; // Modify the range to include the neighboring column
//
//    private static Credential authorize() throws IOException, GeneralSecurityException {
//        HttpTransport httpTransport = new NetHttpTransport();
//        JsonFactory jsonFactory = new JacksonFactory();
//
//        InputStream inputStream = GoogleSheetsExample.class.getResourceAsStream("/credentials.json");
//        GoogleCredential credential = GoogleCredential.fromStream(inputStream, httpTransport, jsonFactory)
//                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
//
//        return credential;
//    }
//
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        Credential credential = authorize();
//        Sheets sheetsService = new Sheets.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, RANGE).execute();
//        List<List<Object>> values = response.getValues();
//        if (values == null || values.isEmpty()) {
//            System.out.println("No data found.");
//        } else {
//            List<ValueRange> updates = new ArrayList<ValueRange>();
//            for (List row : values) {
//                if (row.size() < 2 || row.get(1) != null && !row.get(1).toString().isEmpty()) {
//                    continue; // Skip rows that already have data in the neighboring column or don't have data in the specified column
//                }
//                row.set(1, "Зоя"); // Set the neighboring cell value to "Зоя"
//                ValueRange update = new ValueRange();
//                update.setRange("Sheet1!B" + (values.indexOf(row)+1)); // Set the range for the neighboring cell
//                update.setValues(Arrays.asList(row.subList(1, 2))); // Set the value to be updated
//                updates.add(update); // Add the update request to the list
//            }
//            if (!updates.isEmpty()) {
//                BatchUpdateValuesRequest body = new BatchUpdateValuesRequest()
//                        .setValueInputOption(ValueInputOption.USER_ENTERED)
//                        .setData(updates);
//                UpdateValuesResponse result = sheetsService.spreadsheets().values()
//                        .batchUpdate(SPREADSHEET_ID, body)
//                        .execute();
//                System.out.printf("%d cells updated.", result.getTotalUpdatedCells());
//            }
//        }
//    }
//}
