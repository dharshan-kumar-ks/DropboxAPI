# Dropbox Business API Java Example

This repo demonstrates how to:
- Authenticate a Dropbox Business/Team user using OAuth2 (Authorization Code flow, manual copy-paste)
- Fetch and display the team/organization info
- List all team members using the app
- Fetch and print sign-in (login_success) events for all users - Not yet implemented

I created a trial Business (Standard) account in Dropbox and registered my own Dropbox app at Dropbox App Console.
Stored the app’s client ID, client secret in .env file
<img width="1695" height="748" alt="image" src="https://github.com/user-attachments/assets/62afe14d-5428-4213-9059-05fd0c0a93bf" />
<img width="1695" height="748" alt="image" src="https://github.com/user-attachments/assets/35b6da68-78d7-4ed1-aee3-7a6b260f4dae" />
But I also found a <a href="https://docs.google.com/forms/d/e/1FAIpQLSfkzPmp9srHG9jwE3Uc0bFOwknN-rrLQWr1mf_3FGl86ydCiQ/viewform">form</a> where Dropbox provides us with free Business account for developers: (on request-by-request basis) 

The program prints a Dropbox sign-in link. We need to open it in the browser, authorize the app, and copy the code=... value from the redirect URL & paste the code into the terminal.
I had used <a href="https://github.com/dropbox/dropbox-sdk-java?tab=readme-ov-file#setup">Dropbox Official SDK</a> v5.4.4 instead of direct API calls to my Dropbox application. 

### Example Output
```
1. Open this URL in browser and authorize the app:
https://www.dropbox.com/oauth2/authorize?client_id=ibr4llbt7tl4067&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A53682&scope=team_info.read+members.read+events.read&token_access_type=offline
```
<img width="452" height="389" alt="image" src="https://github.com/user-attachments/assets/1669676d-cb5f-4677-bc5f-b2162b45617d" />


```
2. After authorizing, you'll be redirected to a URL like http://localhost:53682?code=...&state=...
3. Copy and paste the 'code' value here: 4EYjTs0eOrkAAAAAAAAATl3muv7xfGx4hxnis814Dpc

Dropbox token response: {"access_token": "sl.u.AF8LrJzhLkoZX8ZI3SyKqUzUfybzU6xRQoeyA6laXP_J5HlT8r4y0Yf_l2XlQyM1LuAQAC1B_EkhYMWmCccfJ-eBE9ogM4MtMVSeDRoHmK2SZUslrVO_QzS8xKXmY5CI415rB4KyPNjt1KED8z8gmYZAExEAzDljoFKlO_9-5L6_oEIl1CtQCZ4xnwsZQ7DhbD9vKH60b5etO76QLdEHv2bDUgxyBpPpUqj0T-Xsb2RG20adsEoW0qyvu5kkZVK7NjN9oZtb_e6hN01obRCpdjCzRkwjI9cpnq7Wiaz29z0J7i_HcybkM2CKW30z2XS9TjMNDqn9jRoO20B6lkizdLvwaUOdmTpfRSNuScHzomihbevbp8KKHHClQEy1Mwjdnr7c1ZKwKBB7kDo-ipaze4CahtThTw7p23RdwzSJ094sb9MnhH-ZTCgoV4-loqYIQ6_76HOH77VD7MTZSrcMiXUc2HHEmTDRSvx9tgbzeTQbpTbXTMfF8iEDBzFXtIvSX2z0xcKl4Jtk8xhSFeQIGVNtjWmEAq-Te6up-e7AMCLP6aj4zzSNN39HcWoS8ECnp051wxTlXrighf2fhk_uvRFEg9NVBEvAFpjlwdPDWRUNr1Db0THZ8wWuy5msWCBuipnW7gfKwmK0DKGe15mumbSyy1oA7d9pGvLuqXofY5R1Mkw1zizuwNof8Pyh5XwsJsCOLvBBhp5RldID3Ug73EXL1-q2v0qQd7UsxlgGhiJjIAx_4zVemVuk4_EX5fdHNnufcJKo0b9E7Ov6jEU6Xc0vjAL_YnFV3dnnvBooEAZ-x2hYyUD5WouVmQN84gIAWH9ITvVvbWU2E5azAeT-HV9MutDDR08vBOyDdNxQkFWi8J_kkyK_ecv1PgLyqisF2ZeVrtNKafWDU-ocUVGWVbMlUm_h4TuqGyPZbO9tSsK4HYJvrIDM6-vKGNxLOfl3YRdX0ZxwSJFtEYHHjeTI9nYwrN4DPsxv_ztd3TnaLtA-fAMHOtGW9rjbYUm2UQnQt5zHxCEjl0D3d6bDtqogF4d5PGKVQCJrXwBWpjH0mncT6-crL1A39II0hJj4OiRkdwEQLfaTM9rkU_B5hlrDTvazUjth6slsHUL6aep8lmxRUg", "token_type": "bearer", "expires_in": 14400, "refresh_token": "lXcsWwWZ5AEAAAAAAAAAAWMD0qrhX1hkzUx6248fzi0LV0iUnQmZXlkbZXpZLjw-", "scope": "events.read members.read team_info.read", "uid": "", "team_id": "dbtid:AABUnTcBrTfkdFw_cgbfBGQ5-kYcXw0rAIM"}

Access token: sl.u.AF8LrJzhLkoZX8ZI3SyKqUzUfybzU6xRQoeyA6laXP_J5HlT8r4y0Yf_l2XlQyM1LuAQAC1B_EkhYMWmCccfJ-eBE9ogM4MtMVSeDRoHmK2SZUslrVO_QzS8xKXmY5CI415rB4KyPNjt1KED8z8gmYZAExEAzDljoFKlO_9-5L6_oEIl1CtQCZ4xnwsZQ7DhbD9vKH60b5etO76QLdEHv2bDIgxyBpPpUqj0T-Xsb2RG20adsEoW0qyvu5kkZVK7NjN9oZtb_e6hN01obRCpdjCzRkwjI9cpnq7Wiaz29z0J7i_HcybkM2CKW30z2XS9TjMNDqn9jRoO20B6lkizdLvwaUOdmTpfRSNuScHzomihbevbp8KKHHClQEy1Mwjdnr7c1ZKwKBB7kDo-ipaze4CahtThTw7p23RdwzSJ094sb9MnhH-ZTCgoV4-loqYIQ6_76HOH77VD7MTZSrcMiXUc2HHEmTDRSvx9tgbzeTQbpTbXTMfF8iEDBzFXtIvSX2z0xcKl4Jtk8xhSFeQIGVNtjWmEAq-Te6up-e7AMCLP6aj4zzSNN39HcWoS8ECnp051wxTlXrnghf2fhk_uvRFEg9NVBEvAFpjlwdPDWRUNr1Db0THZ8wWuy5msWCBuipnW7gfKwmK0DKGe15mumbSyy1oA7d9pGvLuqXofY5R1Mkw1zizuwNof8Pyh5XwsJsCOLvBBhp5RldID3Ug73EXL1-q2v0qQd7UsxlgGhiJjIAx_4zVemVuk4_EX5fdHNnufcJKo0b9E7Ov6jEU6Xc0vjAL_YnFV3dnnvBooEAZ-x2hYyUD5WouVmQN84gIAWH9ITvVvbWU2E5azAeT-HV9MutDDR08vBOyDdNxQkFWi8J_kkyK_ecv1PgLyqisF2ZeVrtNKafWDU-ocUVGWVbMlUm_h4TuqGyPZbO9tSsK4HYJvrIDM6-vKGNxLOfl3YRdX0ZxwSJFtEYHHjeTU9nYwrN4DPsxv_ztd3TnaLtA-fAMHOtGW9rjbYUm2UQnQt5zHxCEjl0D3d6bDtqogF4d5PGKVQCJrXwBWpjH0mncT6-crL1A39II0hJj4OiRkdwEQLfaTM9rkU_B5hlrDTvazUjth6slsHUL6aep8lmxRUg
Refresh token: lXcsWwWZ5AEAAAAAAAAAAWMD0qrhX1hkzUx6248fzi0LV0iUnQmZXlkbZXpZLjw-
=== Team/Organization Info ===
Team Name: Dharshankumar55
Team ID: dbtid:AABUnTcBrTfkdFw_cgbfBGQ5-kYcXw0rAIM
Licensed users: 5
Provisioned users: 1

=== Team Members ===
Team Member ID: dbmid:AACT4MTvQNHKajwSieDEu6r-BJd6wM3J77E
Email: dharshankumar55@gmail.com
Display Name: Dharshan Kumar
Role: TEAM_ADMIN



Process finished with exit code 0

```
