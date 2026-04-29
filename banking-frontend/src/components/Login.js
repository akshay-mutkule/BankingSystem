import React, { useState } from 'react';
import { TextField, Button, Typography, Container, Paper, Box, Alert } from '@mui/material';
import axios from 'axios';

const Login = ({ onLogin }) => {
  const [accNo, setAccNo] = useState('');
  const [pin, setPin] = useState('');
  const [message, setMessage] = useState('');

  const handleLogin = async () => {
    try {
      const res = await axios.post(`http://localhost:8080/account/login?accNo=${accNo}&pin=${pin}`);
      if (res.status === 200) {
        onLogin(accNo);
        setMessage('');
      }
    } catch (error) {
      setMessage('Invalid Credentials');
    }
  };

  return (
    <Container component="main" maxWidth="sm">
      <Paper elevation={3} sx={{ padding: 4, marginTop: 8 }}>
        <Typography component="h1" variant="h4" align="center" gutterBottom>
          🏦 Banking System Login
        </Typography>
        <Box component="form" sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            label="Account Number"
            value={accNo}
            onChange={(e) => setAccNo(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="PIN"
            type="password"
            value={pin}
            onChange={(e) => setPin(e.target.value)}
          />
          <Button
            type="button"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            onClick={handleLogin}
          >
            Login
          </Button>
          {message && <Alert severity="error">{message}</Alert>}
        </Box>
      </Paper>
    </Container>
  );
};

export default Login;