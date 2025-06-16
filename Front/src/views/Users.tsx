import { useState, useEffect } from "react";
import { allRegisteredUsers } from "@/service/adminService";
import { Spinner } from "@/components";
import "@/styles/ProductsManage.css";

interface SimpleUser {
  name: string;
  role: string;
}

const Users = () => {
  const [registeredUsers, setRegisteredUsers] = useState<SimpleUser[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    allRegisteredUsers()
      .then(res => {
        setRegisteredUsers(res.data);
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
        <strong style={{ fontWeight: "600" }}>AllRegisteredUsers</strong>
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
