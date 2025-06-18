import { useState, useEffect } from "react";
import { allRegisteredUsers, topBuyers } from "@/service/adminService";
import { Spinner } from "@/components";
import "@/styles/ProductsManage.css";

interface SimpleUser {
  name: string;
  role: string;
}

interface UsersProps {
  type: string;
}

const endpointMap = {
  "Top buyers": topBuyers,
  "All users": allRegisteredUsers,
};

const Users = ({ type }: UsersProps) => {
  const [registeredUsers, setRegisteredUsers] = useState<SimpleUser[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const endpoint = endpointMap[type as keyof typeof endpointMap];
    endpoint()
      .then(res => {
        setRegisteredUsers(res.data.data);
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
      <h1 style={{ width: "100%", fontSize: "32px", textAlign: "left" }}>
        <strong style={{ fontWeight: "600" }}>All registered users</strong>
      </h1>
      <div className="search-content">
        {/* <Filter setProducts={setProducts} /> */}
        <div className="search-content-results">
          {registeredUsers.map((registeredUser, index) => (
            <div key={index} className="user-content">
              {registeredUser.name}
              {registeredUser.role}
            </div>
          ))}
        </div>
        {/* <Pagination products={products.pagination}/> */}
      </div>
    </>
  );
};

export default Users;
