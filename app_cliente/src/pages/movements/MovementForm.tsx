import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
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
import { useMutation } from '@tanstack/react-query';
import { createMovement } from '../../services/api';
import type { CreateMovementRequest } from '../../types';

function MovementForm() {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CreateMovementRequest>();

  const mutation = useMutation(createMovement, {
    onSuccess: () => {
      navigate('/movements');
    },
    onError: (err: any) => {
      setError(err.message || 'Error al crear el movimiento');
    },
  });

  const onSubmit = (data: CreateMovementRequest) => {
    mutation.mutate(data);
  };

  return (
    <Paper sx={{ p: 4, maxWidth: 600, mx: 'auto' }}>
      <Typography variant="h4" mb={4}>
        Nuevo Movimiento
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
            <InputLabel>Tipo de Movimiento</InputLabel>
            <Select
              {...register('movementType', { required: 'Este campo es requerido' })}
              error={!!errors.movementType}
              label="Tipo de Movimiento"
            >
              <MenuItem value="CREDIT">Crédito</MenuItem>
              <MenuItem value="DEBIT">Débito</MenuItem>
            </Select>
          </FormControl>

          <TextField
            label="Valor"
            type="number"
            {...register('value', { required: 'Este campo es requerido' })}
            error={!!errors.value}
            helperText={errors.value?.message}
          />

          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end', mt: 2 }}>
            <Button onClick={() => navigate('/movements')}>Cancelar</Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              disabled={mutation.isLoading}
            >
              {mutation.isLoading ? 'Creando...' : 'Crear'}
            </Button>
          </Box>
        </Box>
      </form>
    </Paper>
  );
}

export default MovementForm;