import { allRegisteredUsers, topBuyers } from "@/service/adminService";
import type { PaginationElementDto } from "@/types";
import { Spinner, PaginationNav } from "@/components";
import { useSearchParams } from "react-router-dom";
import { useState, useEffect } from "react";
import "@/styles/Items.css";

interface SimpleUser {
  name: string;
  role: string;
}

interface UsersProps {
  type: string;
}

const endpointMap = {
  "Top compradores": topBuyers,
  Usuarios: allRegisteredUsers,
};

const Users = ({ type }: UsersProps) => {
  const [searchParams] = useSearchParams();
  const [paginationUsers, setPaginationUsers] =
    useState<PaginationElementDto<SimpleUser>>();
  const [isLoading, setIsLoading] = useState(true);

  const page = Number(searchParams.get("page"));

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint(page)
      .then(res => {
        setPaginationUsers(res);
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <>
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>All registered users</strong>
      </h1>
      <div className="items">
        <div className="items-content-wrap">
          {paginationUsers?.data.map((registeredUser, index) => (
            <div key={index} className="items-content-item">
              <p style={{ fontWeight: "600" }}>{registeredUser.name}</p>
              <p>{registeredUser.role}</p>
            </div>
          ))}
        </div>
      </div>
      <PaginationNav pagination={paginationUsers!.pagination} />
    </>
  );
};

export default Users;
