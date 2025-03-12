# ZS-Android-Test
Base application for listening to network status updates on Android

# Details

### Architecture
Base monolit clean arch. Divided by packages on a single module. **`Data -> Domain - UI`**

#### Data
This example contains a repository that will emit information about the network through a Flow that is dependant on the lifecycle of the viewmodel that holds it.

#### Domain
Layer in charge to map data and prepare it for the UI, here we obtain all relevant data contained on the `NetworlCapability` class and expose it on a data class.

#### UI
Collect the flow emitted by the repository and mapped on the use case to then convert the object to a UI friendly data class. 

### Libraries
- Flow
- Jetpack Compose
- Accompanist Compose
- Network, Wifi, Telephony Manager APIs

### Screenshots

<table>
  <tr>
    <th>First Screen</th>
    <th>Permission Rationale request</th>
    <th>Network information Cellular</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/ac55186b-7f6e-4a69-8782-85b0883c1a1e" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/cdfe27b7-7bc1-4e66-bd21-dbd76f786ceb" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/c5ebc0f2-ea5f-4456-918a-92707e565720" width="250"/></td>
  </tr>
</table>

<table>
  <tr>
    <th>Network information Wifi</th>
    <th>Network information No connection</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/c8d3b42f-4b82-4f38-8f4d-8484cf016d3d" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/0218b453-eea8-4d71-b89f-68c9c0a2f787" width="250"/></td>
  </tr>
</table>
