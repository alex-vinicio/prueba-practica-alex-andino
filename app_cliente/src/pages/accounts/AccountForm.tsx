import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import {
  Paper,
  TextField,
  Button,
  Typography,
  Box,
  Alert,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from '@mui/material';
import { useMutation, useQuery } from '@tanstack/react-query';
import { createAccount, updateAccount, getAccountById } from '../../services/api';
import type { Account } from '../../types';

type AccountFormData = Omit<Account, 'id'>;

function AccountForm() {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [error, setError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<AccountFormData>();

  const { data: existingAccount } = useQuery(
    ['account', id],
    () => getAccountById(id!),
    {
      enabled: !!id,
    }
  );

  useEffect(() => {
    if (existingAccount) {
      Object.entries(existingAccount).forEach(([key, value]) => {
        setValue(key as keyof AccountFormData, value);
      });
    }
  }, [existingAccount, setValue]);

  const mutation = useMutation(
    (data: AccountFormData) =>
      id ? updateAccount(id, data) : createAccount(data),
    {
      onSuccess: () => {
        navigate('/accounts');
      },
      onError: (err: Error) => {
        setError(err.message);
      },
    }
  );

  const onSubmit = (data: AccountFormData) => {
    mutation.mutate(data);
  };

  return (
    <Paper sx={{ p: 4, maxWidth: 600, mx: 'auto' }}>
      <Typography variant="h4" mb={4}>
        {id ? 'Editar Cuenta' : 'Nueva Cuenta'}
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <form onSubmit={handleSubmit(onSubmit)}>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          <TextField
            label="Número de Cuenta"
            {...register('accountNumber', { required: 'Este campo es requerido' })}
            error={!!errors.accountNumber}
            helperText={errors.accountNumber?.message}
          />

          <FormControl>
            <InputLabel>Tipo de Cuenta</InputLabel>
            <Select
              {...register('accountType', { required: 'Este campo es requerido' })}
              error={!!errors.accountType}
              label="Tipo de Cuenta"
            >
              <MenuItem value="AHORRO">Ahorro</MenuItem>
              <MenuItem value="CORRIENTE">Corriente</MenuItem>
            </Select>
          </FormControl>

          <TextField
            label="Saldo Inicial"
            type="number"
            {...register('initialBalance', { required: 'Este campo es requerido' })}
            error={!!errors.initialBalance}
            helperText={errors.initialBalance?.message}
          />

          <FormControl>
            <InputLabel>Estado</InputLabel>
            <Select
              {...register('status', { required: 'Este campo es requerido' })}
              error={!!errors.status}
              label="Estado"
            >
              <MenuItem value="ACTIVO">Activo</MenuItem>
              <MenuItem value="INACTIVO">Inactivo</MenuItem>
            </Select>
          </FormControl>

          <TextField
            label="Nombre del Cliente"
            {...register('clientName', { required: 'Este campo es requerido' })}
            error={!!errors.clientName}
            helperText={errors.clientName?.message}
          />

          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end', mt: 2 }}>
            <Button onClick={() => navigate('/accounts')}>Cancelar</Button>
            <Button type="submit" variant="contained" color="primary">
              {id ? 'Actualizar' : 'Crear'}
            </Button>
          </Box>
        </Box>
      </form>
    </Paper>
  );
}

export default AccountForm;