package org.example;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxTeamClientV2;
import com.dropbox.core.v2.team.MembersListResult;
import com.dropbox.core.v2.team.TeamGetInfoResult;
import com.dropbox.core.v2.team.TeamMemberInfo;
import com.dropbox.core.v2.team.TeamMemberProfile;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

public class Main {
    // 1. Authentication
    public static String authenticateWithDropbox(String clientId, String clientSecret, String redirectUri, String scopes) throws Exception {
        // Build the Dropbox authorize URL
        // ex: https://www.dropbox.com/oauth2/authorize?client_id=ibr4llbt7tl4097&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A53682&scope=team_info.read+members.read+events.read&token_access_type=offline
        String authUrl = "https://www.dropbox.com/oauth2/authorize"
                + "?client_id=" + clientId
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(scopes, StandardCharsets.UTF_8)
                + "&token_access_type=offline"; // Will return refresh_token as well

        // We need to autorize in Dropbox page to get
        System.out.println("1. Open this URL in browser and authorize the app:\n" + authUrl);
        // ex: http://localhost:53682/?code=4EYjTs0eOrkAAAAAAAAAS14rOmtl2CMBIoRCSofeZQA
        System.out.println("2. After authorizing, you'll be redirected to a URL like " + redirectUri + "?code=...&state=...");
        // ex: 4EYjTs0eOrkAAAAAAAAAS14rOmtl2CMBIoRCSofeZQA
        System.out.print("3. Copy and paste the 'code' value here: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine().trim();

        // Exchange code for access token
        // ex: https://api.dropbox.com/oauth2/token?code=4EYjTs0eOrkAAAAAAAAAS14rOmtl2CMBIoRCSofeZQA&grant_type=authorization_code&client_id=ibr4llbt7nl4097&client_secret=nwvyha6545tfjy4&redirect_uri=http%3A%2F%2Flocalhost%3A53682
        String tokenUrl = "https://api.dropbox.com/oauth2/token";
        String params = "code=" + URLEncoder.encode(code, "UTF-8") +
                "&grant_type=authorization_code" +
                "&client_id=" + URLEncoder.encode(clientId, "UTF-8") +
                "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8") +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8");
        // Make the POST request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(tokenUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();
        // Get the response
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nDropbox token response: " + resp.body());

        // Parse the JSON response to extract access_token & return it
        JSONObject json = new JSONObject(resp.body());
        if (json.has("access_token")) {
            String accessToken = json.getString("access_token");
            System.out.println("\nAccess token: " + accessToken);
            if (json.has("refresh_token")) {
                System.out.println("Refresh token: " + json.getString("refresh_token"));
            }
            return accessToken;
        } else {
            throw new RuntimeException("Could not fetch access token. Full response: " + resp.body());
        }

    }

    // 2 & 3. Get and display team/org info and plan/license
    public static void printTeamInfo(DbxTeamClientV2 client) throws Exception {
        // Fetch team info; similar to POST /api/2/team/get_info
        TeamGetInfoResult teamInfo = client.team().getInfo();
        // Display some team info
        /*
        {
            "name":"Dharshankumar55",
                "team_id":"dbtid:AABUnTcBrTfkdFw_cgbfBGQ5-kYcXw0rAIM",
                "num_licensed_users":5,
                "num_provisioned_users":1,
                "num_used_licenses":1,
        }
        */
        System.out.println("=== Team/Organization Info ===");
        System.out.println("Team Name: " + teamInfo.getName());
        System.out.println("Team ID: " + teamInfo.getTeamId());
        System.out.println("Licensed users: " + teamInfo.getNumLicensedUsers());
        System.out.println("Provisioned users: " + teamInfo.getNumProvisionedUsers());
        System.out.println();
    }

    // 4. List all users in the organization
    public static void listTeamMembers(DbxTeamClientV2 client) throws Exception {
        System.out.println("=== Team Members ===");
        // Fetch team members; similar to POST /api/2/team/members/list
        MembersListResult memberResult = client.team().membersListBuilder().withLimit(100L).start();
        List<TeamMemberInfo> members = memberResult.getMembers();
        /*
        {
        "members": [
            {
                "profile": {
                    "team_member_id": "dbmid:AACT4MTvQNHKajwSieDEu6r-BJd6wM3J77E",
                    "account_id": "dbid:AAAkg5bDC0_Uuxi8UJVwAMhH5rERWkhR_to",
                    "email": "dharshankumar55@gmail.com",
                    "email_verified": true,
                    "secondary_emails": [],
                    "status": {
                        ".tag": "active"
                    },
                    "name": {
                        "given_name": "Dharshan",
                        "surname": "Kumar",
                        "familiar_name": "Dharshan",
                        "display_name": "Dharshan Kumar",
                        "abbreviated_name": "DK"
        */

        // Display some info about each member
        for (TeamMemberInfo member : members) {
            TeamMemberProfile prof = member.getProfile();
            System.out.println("Team Member ID: " + prof.getTeamMemberId());
            System.out.println("Email: " + prof.getEmail());
            System.out.println("Display Name: " + prof.getName().getDisplayName());
            System.out.println("Role: " + member.getRole().name());
            System.out.println();
        }
        if (memberResult.getHasMore()) {
            // Currently didn't implement pagination
            System.out.println("There are more members to display");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();

        // Get Dropbox app credentials and set redirect URI in app settings
        String clientId = dotenv.get("CLIENT_ID");
        String clientSecret = dotenv.get("CLIENT_SECRET");
        String redirectUri = "http://localhost:53682";
        String scopes = "team_info.read members.read events.read"; // Just limiting to scopes needed

        // Authenticate and get access token interactively
        String accessToken = authenticateWithDropbox(clientId, clientSecret, redirectUri, scopes);

        // Print error message if accessToken is not present
        if (accessToken == null || accessToken.isBlank()) {
            System.err.println("Please set your access token in environment variable: DBX_ACCESS_TOKEN");
            System.exit(1);
        }

        // Create an SDK configuration object (client identifier is used by Dropbox for logging this client's activity)
        DbxRequestConfig config = DbxRequestConfig.newBuilder("cloudeagle-assignment/1.0").build();
        // Create the Dropbox client object
        DbxTeamClientV2 client = new DbxTeamClientV2(config, accessToken);

        // Fetch and display team info and members
        try {
            printTeamInfo(client);
            listTeamMembers(client);
            //printSignInEvents(client);
        } catch (Exception e) {
            System.err.println("Error fetching team info: " + e.getMessage());
        }
    }
}



