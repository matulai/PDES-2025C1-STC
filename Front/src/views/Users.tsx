import { allRegisteredUsers, topBuyers } from "@/service/adminService";
import { useState, useEffect } from "react";
import { Spinner } from "@/components";
import "@/styles/Items.css";

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
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>All registered users</strong>
      </h1>
      <div className="items">
        <div className="items-content-wrap">
          {registeredUsers.map((registeredUser, index) => (
            <div key={index} className="items-content-item">
              <p style={{ fontWeight: "600" }}>{registeredUser.name}</p>
              <p>{registeredUser.role}</p>
            </div>
          ))}
        </div>
        {/* <Pagination products={products.pagination}/> */}
      </div>
    </>
  );
};

export default Users;
