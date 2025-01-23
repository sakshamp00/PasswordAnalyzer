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
      true,
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

// Password Generator function
const generatePassword = () => {
  const upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  const lowerCase = "abcdefghijklmnopqrstuvwxyz";
  const numbers = "0123456789";
  const specialChars = "!@#$%^&*()_+[]{}<>?";
  const allCharacters = upperCase + lowerCase + numbers + specialChars;

  let password = "";
  password += upperCase[Math.floor(Math.random() * upperCase.length)];
  password += lowerCase[Math.floor(Math.random() * lowerCase.length)];
  password += numbers[Math.floor(Math.random() * numbers.length)];
  password += specialChars[Math.floor(Math.random() * specialChars.length)];

  for (let i = 4; i < 12; i++) {
    password += allCharacters[Math.floor(Math.random() * allCharacters.length)];
  }

  return password.split("").sort(() => Math.random() - 0.5).join("");
};

// Strength Checker function
const checkPasswordStrength = (password) => {
  let score = 0;

  if (password.length >= 8) score += 10;
  if (password.length >= 12) score += 10;
  if (/[A-Z]/.test(password)) score += 10;
  if (/[a-z]/.test(password)) score += 10;
  if (/[0-9]/.test(password)) score += 10;
  if (/[^A-Za-z0-9]/.test(password)) score += 10;

  if (score >= 50) return "Strong";
  if (score >= 30) return "Medium";
  return "Weak";
};

function App() {
  const [password, setPassword] = useState("");
  const [key, setKey] = useState(null);
  const [encryptedPassword, setEncryptedPassword] = useState("");
  const [decryptedPassword, setDecryptedPassword] = useState("");
  const [strength, setStrength] = useState("");

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

  // Handle password input changes and update strength
  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);
    setStrength(checkPasswordStrength(newPassword));
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
          onChange={handlePasswordChange}
          placeholder="Type your password here"
        />
        <p className={`strength ${strength.toLowerCase()}`}>
          Strength: {strength}
        </p>
        <div className="buttons">
          <button onClick={generateKey}>Generate Key</button>
          <button onClick={encryptPassword} disabled={!password}>
            Encrypt Password
          </button>
          <button onClick={decryptPassword} disabled={!encryptedPassword}>
            Decrypt Password
          </button>
          <button
            onClick={() => {
              const generated = generatePassword();
              setPassword(generated);
              setStrength(checkPasswordStrength(generated));
            }}
            className="generate-btn"
          >
            Generate Password
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
