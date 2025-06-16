import { getFromLocalStorage } from "@/utils/localStorage";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

function allRegisteredUsers() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allRegisteredUsers`, {
    headers: {
      Authorization: token,
    },
  });
}

function allUsersQualifications() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allUsersQualifications`, {
    headers: {
      Authorization: token,
    },
  });
}

function allFavoritesProducts() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allFavoritesProducts`, {
    headers: {
      Authorization: token,
    },
  });
}

function allUsersPurchases() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allUsersPurchases`, {
    headers: {
      Authorization: token,
    },
  });
}

function topSellingProducts() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/topSellingProducts`, {
    headers: {
      Authorization: token,
    },
  });
}

function topBuyers() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/topBuyers`, {
    headers: {
      Authorization: token,
    },
  });
}

function topFavoriteProducts() {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/topFavoriteProducts`, {
    headers: {
      Authorization: token,
    },
  });
}

export {
  allUsersQualifications,
  allFavoritesProducts,
  topFavoriteProducts,
  allRegisteredUsers,
  topSellingProducts,
  allUsersPurchases,
  topBuyers,
};
