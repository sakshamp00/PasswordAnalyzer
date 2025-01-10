import React, { useState } from "react";
import "./App.css";

function App() {
  const [password, setPassword] = useState("");
  const [key, setKey] = useState(null);
  const [encryptedPassword, setEncryptedPassword] = useState("");
  const [decryptedPassword, setDecryptedPassword] = useState("");

  // Generate a cryptographic AES-GCM key
  const generateKey = async () => {
    const cryptoKey = await window.crypto.subtle.generateKey(
      {
        name: "AES-GCM",
        length: 128, // Key length in bits
      },
      true, // Can be exported
      ["encrypt", "decrypt"]
    );
    setKey(cryptoKey);
    alert("Encryption key generated!");
  };

  // Encrypt the password
  const encryptPassword = async () => {
    if (!key) {
      alert("Please generate a key first!");
      return;
    }
    try {
      const iv = window.crypto.getRandomValues(new Uint8Array(12)); // Random Initialization Vector (IV)
      const encodedPassword = new TextEncoder().encode(password); // Encode password as bytes
      const encrypted = await window.crypto.subtle.encrypt(
        {
          name: "AES-GCM",
          iv,
        },
        key,
        encodedPassword
      );

      // Combine encrypted data and IV, and encode as Base64
      setEncryptedPassword(
        btoa(
          String.fromCharCode(...new Uint8Array(encrypted)) +
            ":" +
            String.fromCharCode(...iv)
        )
      );
    } catch (error) {
      console.error("Encryption failed:", error);
    }
  };

  // Decrypt the password
  const decryptPassword = async () => {
    if (!key) {
      alert("Please generate a key first!");
      return;
    }
    try {
      const [cipherText, iv] = encryptedPassword.split(":").map((part) =>
        Uint8Array.from(atob(part), (c) => c.charCodeAt(0))
      );
      const decrypted = await window.crypto.subtle.decrypt(
        {
          name: "AES-GCM",
          iv,
        },
        key,
        cipherText
      );

      setDecryptedPassword(new TextDecoder().decode(decrypted));
    } catch (error) {
      console.error("Decryption failed:", error);
      alert("Failed to decrypt the password.");
    }
  };

  return (
    <div className="container">
      <h1>Password Analyzer</h1>
      <div className="form-group">
        <label htmlFor="password">Enter Password:</label>
        <input
          type="text"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Enter your password"
        />
        <button onClick={generateKey}>Generate Key</button>
        <button onClick={encryptPassword}>Encrypt Password</button>
        <button onClick={decryptPassword}>Decrypt Password</button>
      </div>
      <div className="output">
        {encryptedPassword && (
          <p>
            <strong>Encrypted Password:</strong> {encryptedPassword}
          </p>
        )}
        {decryptedPassword && (
          <p>
            <strong>Decrypted Password:</strong> {decryptedPassword}
          </p>
        )}
      </div>
    </div>
  );
}

export default App;