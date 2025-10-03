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
import { createClient, updateClient, getClientById } from '../../services/api';
import type { Client } from '../../types';

type ClientFormData = Omit<Client, 'id'>;

function ClientForm() {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [error, setError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<ClientFormData>();

  const { data: existingClient } = useQuery(
    ['client', id],
    () => getClientById(id!),
    {
      enabled: !!id,
    }
  );

  useEffect(() => {
    if (existingClient) {
      Object.entries(existingClient).forEach(([key, value]) => {
        setValue(key as keyof ClientFormData, value);
      });
    }
  }, [existingClient, setValue]);

  const mutation = useMutation(
    (data: ClientFormData) =>
      id ? updateClient(id, data) : createClient(data),
    {
      onSuccess: () => {
        navigate('/clients');
      },
      onError: (err: any) => {
        setError(err.message || 'Error al guardar el cliente');
      },
    }
  );

  const onSubmit = (data: ClientFormData) => {
    mutation.mutate(data);
  };

  return (
    <Paper sx={{ p: 4, maxWidth: 600, mx: 'auto' }}>
      <Typography variant="h4" mb={4}>
        {id ? 'Editar Cliente' : 'Nuevo Cliente'}
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <form onSubmit={handleSubmit(onSubmit)}>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          <TextField
            label="Identificación"
            {...register('identifier', { required: 'Este campo es requerido' })}
            error={!!errors.identifier}
            helperText={errors.identifier?.message}
          />

          <TextField
            label="Nombre"
            {...register('name', { required: 'Este campo es requerido' })}
            error={!!errors.name}
            helperText={errors.name?.message}
          />

          <FormControl>
            <InputLabel>Género</InputLabel>
            <Select
              {...register('gender', { required: 'Este campo es requerido' })}
              error={!!errors.gender}
              label="Género"
            >
              <MenuItem value="HOMBRE">Hombre</MenuItem>
              <MenuItem value="MUJER">Mujer</MenuItem>
            </Select>
          </FormControl>

          <TextField
            label="Edad"
            type="number"
            {...register('age', { required: 'Este campo es requerido' })}
            error={!!errors.age}
            helperText={errors.age?.message}
          />

          <TextField
            label="Dirección"
            {...register('address', { required: 'Este campo es requerido' })}
            error={!!errors.address}
            helperText={errors.address?.message}
          />

          <TextField
            label="Teléfono"
            {...register('phone', { required: 'Este campo es requerido' })}
            error={!!errors.phone}
            helperText={errors.phone?.message}
          />

          <TextField
            label="Email"
            type="email"
            {...register('email', { required: 'Este campo es requerido' })}
            error={!!errors.email}
            helperText={errors.email?.message}
          />

          <TextField
            label="Contraseña"
            type="password"
            {...register('password', { required: 'Este campo es requerido' })}
            error={!!errors.password}
            helperText={errors.password?.message}
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

          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end', mt: 2 }}>
            <Button onClick={() => navigate('/clients')}>Cancelar</Button>
            <Button type="submit" variant="contained" color="primary">
              {id ? 'Actualizar' : 'Crear'}
            </Button>
          </Box>
        </Box>
      </form>
    </Paper>
  );
}

export default ClientForm;