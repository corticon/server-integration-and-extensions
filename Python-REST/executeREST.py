
# Python sample for making a REST execution call to a Corticon decision service.

import requests
import json

# Declare connection properties
# Note: The Corticon Server runs on port 8080
corticonServerProtocol = 'http'
corticonServerAddress = 'localhost'
corticonServerPort = '8080'
corticonServerPath = '/axis/corticon/execute'

# File location for the new payload
# ** This line has been updated to your specific path **
jsonFilePath = r"C:\Users\smeldon\Projects\PDP Auto Insurance\input.json"

# Read the file
try:
    with open(jsonFilePath, 'r') as f:
        jsonfileContents = f.read()
except FileNotFoundError:
    print(f"Error: The file was not found at {jsonFilePath}")
    print("Please make sure the jsonFilePath variable is correct.")
    exit()

# Connect to the server
corticonURL = f"{corticonServerProtocol}://{corticonServerAddress}:{corticonServerPort}{corticonServerPath}"

print(f"Sending request to: {corticonURL}")

try:
    response = requests.post(corticonURL, data=jsonfileContents, headers={'Content-type': 'application/json'})
    response.raise_for_status()  # This will raise an exception for HTTP error codes

    # Pretty-print the JSON response
    responseJSON = response.json()
    print("\n--- Decision Service Response ---")
    print(json.dumps(responseJSON, indent=2, sort_keys=True))

except requests.exceptions.RequestException as e:
    print(f"\nAn error occurred during the request: {e}")