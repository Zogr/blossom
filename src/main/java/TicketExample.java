import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Status;
import org.zendesk.client.v2.model.Ticket;
import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketExample {
    public static void main(String[] args) {


        String subdomain = "apalon";
        String email = "botanist@apalon.com";
        String password = "BlossomPlant70";

        Zendesk zd = new Zendesk.Builder("https://" + subdomain + ".zendesk.com")
                .setUsername(email)
                .setPassword(password)
                .build();


        // Create a list to store the ticket IDs
        List<String> ticketIds = new ArrayList<>();
        Iterable<Ticket> tickets = zd.getTickets();
        for (Ticket t : tickets) {
            if (t.getStatus().equals(Status.NEW)) {
                ticketIds.add("https://apalon.zendesk.com/agent/tickets/" + t.getId() + "\n");
            }
        }
        System.out.println(ticketIds);
        System.out.println(ticketIds.size());

        try {
            FileWriter  fileW = new FileWriter("new_tickets.txt");
            fileW.write(String.valueOf (ticketIds));
                        fileW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}



