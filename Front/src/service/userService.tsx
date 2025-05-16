import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

function getCurrentUser(token: string) {
  return axios.get(`${API_URL}/user/me`, {
    headers: {
      Authorization: token,
    },
  });
}

export { getCurrentUser };
