import type { Qualification } from "@/types";
import { qualifyProduct } from "@/service/userService";
import { SendIcon } from "@/icons";
import { useState } from "react";
import { useAuth } from "@/hooks";
import StarsQualify from "./StarsQualify";
import CommentItem from "./CommentItem";
import InputText from "./InputText";
import "@/styles/CommentsSection.css";

interface CommentsSectionProps {
  productName: string;
  comments: Qualification[];
  setComments: React.Dispatch<React.SetStateAction<Qualification[]>>;
}

const CommentsSection = ({
  productName,
  comments,
  setComments,
}: CommentsSectionProps) => {
  const { user } = useAuth();

  const [comment, setComment] = useState<Qualification>({
    userName: user?.name!,
    productName: productName,
    comment: "",
    score: 0,
  });

  const handleSetValue = (value: number) => {
    if (value && value > 0 && value <= 5) {
      comment.score = value;
      setComment({ ...comment });
    }
  };

  const handleAddComment = (text: string) => {
    if (text && comment.score > 0) {
      comment.comment = text;
      qualifyProduct(comment)
        .then(_res => {
          user?.qualifications.push(comment);
          setComments(prev => [...prev, comment]);
          setComment({
            ...comment,
            comment: "",
            score: 0,
          });
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  const canComment =
    user !== null &&
    user?.purchases.some(pr =>
      pr.purchaseProducts.some(p => p.name === productName)
    ) &&
    !user?.qualifications.some(q => q.productName === productName);

  return (
    <section className="comments-section">
      <div className="comments-section-list-container">
        <section className="comments-section-list-container-content">
          {comments.map((comment, index) => (
            <CommentItem
              key={comment.comment + index}
              userName={comment.userName}
              text={comment.comment}
              value={comment.score}
            />
          ))}
        </section>
      </div>
      {canComment && (
        <section className="comments-section-qualify">
          <StarsQualify
            value={comment.score}
            starsSize={20}
            setValue={handleSetValue}
          />
          <InputText
            handleSubmit={handleAddComment}
            iconComponent={<SendIcon />}
            placeholder="Agrega un comentario..."
            id="comment-input"
          />
        </section>
      )}
    </section>
  );
};

export default CommentsSection;
