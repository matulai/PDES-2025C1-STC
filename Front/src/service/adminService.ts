import type {
  PaginationElementDto,
  Product,
  PurchaseRecipe,
  Qualification,
  User,
} from "@/types";
import { getFromLocalStorage } from "@/utils/localStorage";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

async function allRegisteredUsers(
  page = 1,
  size = 25
): Promise<PaginationElementDto<User>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get(`${API_URL}/admin/allRegisteredUsers`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
  return data;
}

async function allUsersQualifications(
  page = 1,
  size = 10
): Promise<PaginationElementDto<Qualification>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get(`${API_URL}/admin/allUsersQualifications`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
  return data;
}

async function allFavoritesProducts(
  page = 1,
  size = 25
): Promise<PaginationElementDto<Product>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get(`${API_URL}/admin/allFavoritesProducts`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
  return data;
}

async function allUsersPurchases(
  page = 1,
  size = 5
): Promise<PaginationElementDto<PurchaseRecipe>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get<PaginationElementDto<PurchaseRecipe>>(
    `${API_URL}/admin/allUsersPurchases`,
    {
      params: {
        page,
        size,
      },
      headers: {
        Authorization: token,
      },
    }
  );
  return data;
}

async function topSellingProducts(
  page = 1,
  size = 5
): Promise<PaginationElementDto<Product>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get(`${API_URL}/admin/topSellingProducts`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
  return data;
}

async function topBuyers(
  page = 1,
  size = 5
): Promise<PaginationElementDto<User>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get(`${API_URL}/admin/topBuyers`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
  return data;
}

async function topFavoriteProducts(
  page = 1,
  size = 5
): Promise<PaginationElementDto<Product>> {
  const token = getFromLocalStorage("token");

  const { data } = await axios.get(`${API_URL}/admin/topFavoriteProducts`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
  return data;
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
