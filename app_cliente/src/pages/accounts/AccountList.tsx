import { useState } from 'react';
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
  Typography,
  Box,
  IconButton,
  Alert,
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getAccounts, deleteAccount } from '../../services/api';
import type { Account } from '../../types';

function AccountList() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [error, setError] = useState<string | null>(null);

  const { data: accounts, isLoading } = useQuery<Account[]>(['accounts'], getAccounts);

  const deleteMutation = useMutation(
    (accountNumber: string) => deleteAccount(accountNumber),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['accounts']);
        setError(null);
      },
      onError: (error: any) => {
        setError(error.message || 'Error al eliminar la cuenta');
      },
    }
  );

  if (isLoading) return <Typography>Cargando...</Typography>;

  return (
    <div>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
        <Typography variant="h4">Cuentas</Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate('/accounts/new')}
        >
          Nueva Cuenta
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Número de Cuenta</TableCell>
              <TableCell>Tipo de Cuenta</TableCell>
              <TableCell>Saldo Inicial</TableCell>
              <TableCell>Estado</TableCell>
              <TableCell>Cliente</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {accounts?.map((account) => (
              <TableRow key={account.accountNumber}>
                <TableCell>{account.accountNumber}</TableCell>
                <TableCell>{account.accountType}</TableCell>
                <TableCell>${account.initialBalance}</TableCell>
                <TableCell>{account.status}</TableCell>
                <TableCell>{account.clientName}</TableCell>
                <TableCell>
                  <IconButton
                    color="primary"
                    onClick={() => navigate(`/accounts/edit/${account.accountNumber}`)}
                  >
                    <EditIcon />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => deleteMutation.mutate(account.accountNumber)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default AccountList;