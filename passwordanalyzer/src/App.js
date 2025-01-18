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
        length: 128,
      },
      true,
      ["encrypt", "decrypt"]
    );
    setKey(cryptoKey);
    alert("Encryption key generated successfully!");
  };

  // Encrypt the password
  const encryptPassword = async () => {
    if (!key) {
      alert("Please generate a key first!");
      return;
    }
    try {
      const iv = window.crypto.getRandomValues(new Uint8Array(12));
      const encodedPassword = new TextEncoder().encode(password);
      const encrypted = await window.crypto.subtle.encrypt(
        {
          name: "AES-GCM",
          iv,
        },
        key,
        encodedPassword
      );

      // Encode IV and encrypted data into Base64
      const encryptedBase64 = btoa(
        String.fromCharCode(...new Uint8Array(encrypted))
      );
      const ivBase64 = btoa(String.fromCharCode(...iv));
      setEncryptedPassword(`${encryptedBase64}:${ivBase64}`);
    } catch (error) {
      console.error("Encryption failed:", error);
      alert("Failed to encrypt the password.");
    }
  };

  // Decrypt the password
  const decryptPassword = async () => {
    if (!key) {
      alert("Please generate a key first!");
      return;
    }
    try {
      const [encryptedData, iv] = encryptedPassword
        .split(":")
        .map((part) => Uint8Array.from(atob(part), (c) => c.charCodeAt(0)));
      const decrypted = await window.crypto.subtle.decrypt(
        {
          name: "AES-GCM",
          iv,
        },
        key,
        encryptedData
      );
      setDecryptedPassword(new TextDecoder().decode(decrypted));
    } catch (error) {
      console.error("Decryption failed:", error);
      alert("Failed to decrypt the password.");
    }
  };

  return (
    <div className="container">
      <h1>Password Manager</h1>
      <div className="form-group">
        <label htmlFor="password">Enter Your Password</label>
        <input
          type="text"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Type your password here"
        />
        <div className="buttons">
          <button onClick={generateKey}>Generate Key</button>
          <button onClick={encryptPassword} disabled={!password}>
            Encrypt Password
          </button>
          <button onClick={decryptPassword} disabled={!encryptedPassword}>
            Decrypt Password
          </button>
        </div>
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
