import React, { useState } from "react";
import "./App.css";

// AES encryption utility functions
const passwordEncryptor = {
  async generateKey() {
    return await window.crypto.subtle.generateKey(
      {
        name: "AES-GCM",
        length: 128, // AES key length
      },
      true, // Can be exported
      ["encrypt", "decrypt"]
    );
  },

  async encrypt(password, key) {
    try {
      const iv = window.crypto.getRandomValues(new Uint8Array(12)); // Initialization Vector
      const encodedPassword = new TextEncoder().encode(password);
      const encrypted = await window.crypto.subtle.encrypt(
        {
          name: "AES-GCM",
          iv,
        },
        key,
        encodedPassword
      );

      // Encode the encrypted data and IV to Base64
      const encryptedBase64 = btoa(
        String.fromCharCode(...new Uint8Array(encrypted))
      );
      const ivBase64 = btoa(String.fromCharCode(...iv));
      return `${encryptedBase64}:${ivBase64}`;
    } catch (error) {
      console.error("Encryption failed:", error);
      throw new Error("Encryption error");
    }
  },

  async decrypt(encryptedPassword, key) {
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

      return new TextDecoder().decode(decrypted);
    } catch (error) {
      console.error("Decryption failed:", error);
      throw new Error("Decryption error");
    }
  },
};

function App() {
  const [password, setPassword] = useState("");
  const [key, setKey] = useState(null);
  const [encryptedPassword, setEncryptedPassword] = useState("");
  const [decryptedPassword, setDecryptedPassword] = useState("");

  // Generate encryption key
  const generateKey = async () => {
    try {
      const generatedKey = await passwordEncryptor.generateKey();
      setKey(generatedKey);
      alert("Encryption key generated successfully!");
    } catch (error) {
      alert("Error generating key.");
    }
  };

  // Encrypt password using passwordEncryptor
  const encryptPassword = async () => {
    if (!key) {
      alert("Please generate a key first!");
      return;
    }
    try {
      const encrypted = await passwordEncryptor.encrypt(password, key);
      setEncryptedPassword(encrypted);
    } catch (error) {
      alert("Failed to encrypt password.");
    }
  };

  // Decrypt password using passwordEncryptor
  const decryptPassword = async () => {
    if (!key) {
      alert("Please generate a key first!");
      return;
    }
    try {
      const decrypted = await passwordEncryptor.decrypt(encryptedPassword, key);
      setDecryptedPassword(decrypted);
    } catch (error) {
      alert("Failed to decrypt password.");
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
