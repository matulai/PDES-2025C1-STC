import { allUsersQualifications } from "@/service/adminService";
import { useState, useEffect } from "react";
import type { Qualification } from "@/types";
import { Spinner } from "@/components";
import "@/styles/Items.css";

const Qualifications = () => {
  const [usersQualifications, setUsersQualifications] = useState<
    Qualification[]
  >([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    allUsersQualifications()
      .then(res => {
        setUsersQualifications(res.data.data);
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
        <strong style={{ fontWeight: "600" }}>All users qualifications</strong>
      </h1>
      <div className="items">
        <div className="items-content-column">
          {usersQualifications.map((userQualifications, index) => (
            <div key={index} className="items-content-item">
              <p>
                <b style={{ fontWeight: "600" }}>
                  {userQualifications.userName}
                </b>{" "}
                hizo un comentario en el producto:{" "}
                {userQualifications.productName}
              </p>
              <p>comentario: "{userQualifications.comment}"</p>
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
