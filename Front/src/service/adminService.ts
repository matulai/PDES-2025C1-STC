import { getFromLocalStorage } from "@/utils/localStorage";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

function allRegisteredUsers(page = 1, size = 25) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allRegisteredUsers`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function allUsersQualifications(page = 1, size = 10) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allUsersQualifications`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function allFavoritesProducts(page = 1, size = 25) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allFavoritesProducts`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function allUsersPurchases(page = 1, size = 5) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/allUsersPurchases`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function topSellingProducts(page = 1, size = 25) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/topSellingProducts`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function topBuyers(page = 1, size = 25) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/topBuyers`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function topFavoriteProducts(page = 1, size = 25) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/admin/topFavoriteProducts`, {
    params: {
      page,
      size,
    },
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
