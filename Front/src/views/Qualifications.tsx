import { allUsersQualifications } from "@/service/adminService";
import { useState, useEffect } from "react";
import type { Qualification } from "@/types";
import { Spinner } from "@/components";
import "@/styles/ProductsManage.css";

const Qualifications = () => {
  const [usersQualifications, setUsersQualifications] = useState<
    Qualification[]
  >([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    allUsersQualifications()
      .then(res => {
        setUsersQualifications(res.data);
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
        <strong style={{ fontWeight: "600" }}>AllUsersQualifications</strong>
      </h1>
      <div className="search-content">
        {/* <Filter setProducts={setProducts} /> */}
        <div className="qualification-content">
          {usersQualifications.map((userQualifications, index) => (
            <div key={index} className="user-content">
              <p>
                {userQualifications.userName}
                {" On "}
                {userQualifications.productName}
              </p>
              <div>{userQualifications.comment}</div>
              <p>score: {userQualifications.score}</p>
            </div>
          ))}
        </div>
        {/* <Pagination products={products.pagination}/> */}
      </div>
    </>
  );
};

export default Qualifications;
