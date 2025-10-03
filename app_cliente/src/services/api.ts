import axios from 'axios';
import { Client, Account, Movement, CreateMovementRequest } from '../types';

const CLIENT_API_URL = 'http://localhost:8090/api/v1';
const ACCOUNT_MOVEMENT_API_URL = 'http://localhost:8095/api/v1';

// Cliente API instance
const clientApi = axios.create({
  baseURL: CLIENT_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Cuenta y Movimientos API instance
const accountApi = axios.create({
  baseURL: ACCOUNT_MOVEMENT_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Error handling interceptor for both instances
const errorInterceptor = (error: any) => {
  if (error.response?.status === 400) {
    return Promise.reject(error.response.data);
  }
  return Promise.reject(error);
};

clientApi.interceptors.response.use((response) => response, errorInterceptor);
accountApi.interceptors.response.use((response) => response, errorInterceptor);

// Clients
export const getClients = () => 
  clientApi.get<Client[]>('/clients').then(response => response.data);

export const getClientById = (id: string) =>
  clientApi.get<Client>(`/clients/${id}`).then(response => response.data);

export const createClient = (client: Omit<Client, 'id'>) =>
  clientApi.post<Client>('/clients', client).then(response => response.data);

export const updateClient = (id: string, client: Partial<Client>) =>
  clientApi.put<Client>(`/clients/${id}`, client).then(response => response.data);

export const deleteClient = (id: string) =>
  clientApi.delete(`/clients/${id}`).then(response => response.data);

// Accounts
export const getAccounts = () =>
  accountApi.get<Account[]>('/accounts').then(response => response.data);

export const getAccountById = (id: string) =>
  accountApi.get<Account>(`/accounts/${id}`).then(response => response.data);

export const createAccount = (account: Omit<Account, 'id'>) =>
  accountApi.post<Account>('/accounts', account).then(response => response.data);

export const updateAccount = (id: string, account: Partial<Account>) =>
  accountApi.put<Account>(`/accounts/${id}`, account).then(response => response.data);

export const deleteAccount = (id: string) =>
  accountApi.delete(`/accounts/${id}`).then(response => response.data);

// Movements
export const getMovements = () =>
  accountApi.get<Movement[]>('/movements').then(response => response.data);

export const getMovementsByAccount = (accountNumber: string) =>
  accountApi.get<Movement[]>(`/movements/account/${accountNumber}`).then(response => response.data);

export const getMovementsByDateAndUser = (date: string, userName: string) =>
  accountApi.get<Movement[]>(`/movements/search`, {
    params: {
      date,
      userName
    }
  }).then(response => response.data);

export const createMovement = (movement: CreateMovementRequest) =>
  accountApi.post<Movement>('/movements', movement).then(response => response.data);

