import React, { useState, useEffect } from 'react';
import {
  Container,
  Paper,
  Typography,
  Button,
  Box,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Alert,
  Card,
  CardContent,
  Grid
} from '@mui/material';
import axios from 'axios';

const Dashboard = ({ accNo, onLogout }) => {
  const [balance, setBalance] = useState(0);
  const [transactions, setTransactions] = useState([]);
  const [toAcc, setToAcc] = useState('');
  const [transferAmount, setTransferAmount] = useState('');
  const [message, setMessage] = useState('');
  const [openDeposit, setOpenDeposit] = useState(false);
  const [openWithdraw, setOpenWithdraw] = useState(false);
  const [openTransfer, setOpenTransfer] = useState(false);
  const [amount, setAmount] = useState('');

  useEffect(() => {
    fetchBalance();
    fetchHistory();
  }, []);

  const fetchBalance = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/account/balance?accNo=${accNo}`);
      setBalance(res.data);
    } catch (error) {
      console.error('Error fetching balance');
    }
  };

  const fetchHistory = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/account/history?accNo=${accNo}`);
      setTransactions(res.data);
    } catch (error) {
      console.error('Error fetching history');
    }
  };

  const handleDeposit = async () => {
    try {
      await axios.post(`http://localhost:8080/account/deposit?accNo=${accNo}&amount=${amount}`);
      setMessage('Deposit Successful');
      fetchBalance();
      fetchHistory();
      setOpenDeposit(false);
      setAmount('');
    } catch (error) {
      setMessage('Deposit Failed');
    }
  };

  const handleWithdraw = async () => {
    try {
      await axios.post(`http://localhost:8080/account/withdraw?accNo=${accNo}&amount=${amount}`);
      setMessage('Withdraw Successful');
      fetchBalance();
      fetchHistory();
      setOpenWithdraw(false);
      setAmount('');
    } catch (error) {
      setMessage('Withdraw Failed');
    }
  };

  const handleTransfer = async () => {
    try {
      const res = await axios.post(`http://localhost:8080/account/transfer?fromAcc=${accNo}&toAcc=${toAcc}&amount=${transferAmount}`);
      setMessage(res.data);
      fetchBalance();
      fetchHistory();
      setOpenTransfer(false);
      setToAcc('');
      setTransferAmount('');
    } catch (error) {
      setMessage('Transfer Failed');
    }
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Typography variant="h4" align="center" gutterBottom>
        Welcome to Your Banking Dashboard
      </Typography>
      <Typography variant="h6" align="center" gutterBottom>
        Account: {accNo}
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Balance</Typography>
              <Typography variant="h4" color="primary">₹{balance}</Typography>
              <Button variant="outlined" onClick={fetchBalance} sx={{ mt: 1 }}>
                Refresh
              </Button>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={8}>
          <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
            <Button variant="contained" color="success" onClick={() => setOpenDeposit(true)}>
              Deposit
            </Button>
            <Button variant="contained" color="error" onClick={() => setOpenWithdraw(true)}>
              Withdraw
            </Button>
            <Button variant="contained" color="primary" onClick={() => setOpenTransfer(true)}>
              Transfer
            </Button>
            <Button variant="outlined" onClick={fetchHistory}>
              View History
            </Button>
            <Button variant="text" onClick={onLogout}>
              Logout
            </Button>
          </Box>
        </Grid>
      </Grid>

      {message && <Alert severity="info" sx={{ mt: 2 }}>{message}</Alert>}

      {transactions.length > 0 && (
        <Paper sx={{ mt: 4 }}>
          <Typography variant="h6" sx={{ p: 2 }}>Transaction History</Typography>
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Amount</TableCell>
                  <TableCell>Date</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {transactions.map((t) => (
                  <TableRow key={t.id}>
                    <TableCell>{t.id}</TableCell>
                    <TableCell>{t.type}</TableCell>
                    <TableCell
                      sx={{
                        color: t.type === 'DEPOSIT' ? 'green' : t.type === 'WITHDRAW' ? 'red' : 'blue',
                        fontWeight: 'bold'
                      }}
                    >
                      ₹{t.amount}
                    </TableCell>
                    <TableCell>{t.date}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Paper>
      )}

      {/* Deposit Dialog */}
      <Dialog open={openDeposit} onClose={() => setOpenDeposit(false)}>
        <DialogTitle>Deposit Money</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDeposit(false)}>Cancel</Button>
          <Button onClick={handleDeposit}>Deposit</Button>
        </DialogActions>
      </Dialog>

      {/* Withdraw Dialog */}
      <Dialog open={openWithdraw} onClose={() => setOpenWithdraw(false)}>
        <DialogTitle>Withdraw Money</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenWithdraw(false)}>Cancel</Button>
          <Button onClick={handleWithdraw}>Withdraw</Button>
        </DialogActions>
      </Dialog>

      {/* Transfer Dialog */}
      <Dialog open={openTransfer} onClose={() => setOpenTransfer(false)}>
        <DialogTitle>Transfer Money</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Receiver Account"
            type="text"
            fullWidth
            value={toAcc}
            onChange={(e) => setToAcc(e.target.value)}
          />
          <TextField
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            value={transferAmount}
            onChange={(e) => setTransferAmount(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenTransfer(false)}>Cancel</Button>
          <Button onClick={handleTransfer}>Transfer</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default Dashboard;