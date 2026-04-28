import React, { useState } from "react";

function App() {
  const [accNo, setAccNo] = useState("");
  const [pin, setPin] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);
  const [message, setMessage] = useState("");
  const [balance, setBalance] = useState("");
  const [transactions, setTransactions] = useState([]);

  const [toAcc, setToAcc] = useState("");
  const [transferAmount, setTransferAmount] = useState("");

  // LOGIN
  const login = async () => {
    try {
      const res = await fetch(
        `http://localhost:8080/account/login?accNo=${accNo}&pin=${pin}`,
        { method: "POST" }
      );

      if (res.ok) {
        setLoggedIn(true);
        setMessage("");
      } else {
        setMessage("Invalid Credentials ❌");
      }
    } catch {
      setMessage("Server Error ❌");
    }
  };

  // BALANCE
  const getBalance = async () => {
    const res = await fetch(
      `http://localhost:8080/account/balance?accNo=${accNo}`
    );
    const data = await res.text();
    setBalance(data);
  };

  // DEPOSIT
  const deposit = async () => {
    const amount = prompt("Enter amount:");
    await fetch(
      `http://localhost:8080/account/deposit?accNo=${accNo}&amount=${amount}`,
      { method: "POST" }
    );
    alert("Deposit Successful");
  };

  // WITHDRAW
  const withdraw = async () => {
    const amount = prompt("Enter amount:");
    await fetch(
      `http://localhost:8080/account/withdraw?accNo=${accNo}&amount=${amount}`,
      { method: "POST" }
    );
    alert("Withdraw Successful");
  };

  // TRANSFER
  const transfer = async () => {
    try {
      const res = await fetch(
        `http://localhost:8080/account/transfer?fromAcc=${accNo}&toAcc=${toAcc}&amount=${transferAmount}`,
        { method: "POST" }
      );

      const data = await res.text();
      setMessage(data);

      setToAcc("");
      setTransferAmount("");
    } catch {
      setMessage("Transfer Failed ❌");
    }
  };

  // HISTORY
  const getHistory = async () => {
    try {
      const res = await fetch(
        `http://localhost:8080/account/history?accNo=${accNo}`
      );
      const data = await res.json();
      setTransactions(data);
    } catch {
      alert("Error loading history");
    }
  };

  // LOGOUT
  const logout = () => {
    setLoggedIn(false);
    setAccNo("");
    setPin("");
    setBalance("");
    setTransactions([]);
  };

  // LOGIN UI
  if (!loggedIn) {
    return (
      <div style={{ textAlign: "center", marginTop: "100px" }}>
        <h2>🏦 Banking Login</h2>

        <input
          type="text"
          placeholder="Account Number"
          value={accNo}
          onChange={(e) => setAccNo(e.target.value)}
        />
        <br /><br />

        <input
          type="password"
          placeholder="PIN"
          value={pin}
          onChange={(e) => setPin(e.target.value)}
        />
        <br /><br />

        <button onClick={login}>Login</button>

        <h3>{message}</h3>
      </div>
    );
  }

  // DASHBOARD UI
  return (
    <div style={{ textAlign: "center", marginTop: "40px" }}>
      <h2>Welcome Account: {accNo}</h2>

      <button onClick={getBalance}>Check Balance</button>
      <br /><br />

      {balance && <h3>Balance: ₹{balance}</h3>}

      <br />

      <button onClick={deposit}>Deposit</button>
      <br /><br />

      <button onClick={withdraw}>Withdraw</button>
      <br /><br />

      <button onClick={getHistory}>View Transactions</button>

      <hr />

      <h3>Transfer Money</h3>

      <input
        type="text"
        placeholder="Receiver Account"
        value={toAcc}
        onChange={(e) => setToAcc(e.target.value)}
      />
      <br /><br />

      <input
        type="number"
        placeholder="Amount"
        value={transferAmount}
        onChange={(e) => setTransferAmount(e.target.value)}
      />
      <br /><br />

      <button onClick={transfer}>Send Money</button>

      <h3>{message}</h3>

      {/* TRANSACTION TABLE */}
      {transactions.length > 0 && (
        <div style={{ marginTop: "30px" }}>
          <h3>Transaction History</h3>

          <table border="1" style={{ margin: "auto" }}>
            <thead>
              <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Date</th>
              </tr>
            </thead>

            <tbody>
              {transactions.map((t) => (
                <tr key={t.id}>
                  <td>{t.id}</td>
                  <td>{t.type}</td>
                  <td>₹{t.amount}</td>
                  <td>{t.date}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <br /><br />

      <button onClick={logout}>Logout</button>
    </div>
  );
}

export default App;