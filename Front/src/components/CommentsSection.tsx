import type { Comment } from "@/types";
import { SendIcon } from "@/icons";
import { useState } from "react";
import InputText from "./InputText";
import StarsQualify from "./StarsQualify";
import CommentItem from "./CommentItem";
import "@/styles/CommentsSection.css";

interface CommentsSectionProps {
  comments: Comment[];
  setComments: React.Dispatch<React.SetStateAction<Comment[]>>;
}

const CommentsSection = ({ comments, setComments }: CommentsSectionProps) => {
  const [comment, setComment] = useState<Comment>({
    text: "",
    value: 0,
  });

  const handleSetValue = (value: number) => {
    if (value && value > 0 && value <= 5) {
      comment.value = value;
      setComment({ ...comment });
    }
  };

  const handleAddComment = (text: string) => {
    if (text && comment.value > 0) {
      comment.text = text;
      setComments(prev => [...prev, comment]);
    }
  };

  return (
    <section className="comments-section">
      <div className="comments-section-list-container">
        <section className="comments-section-list-container-content">
          {comments.map((comment, index) => (
            <CommentItem
              key={comment.text + index}
              text={comment.text}
              value={comment.value}
            />
          ))}
        </section>
      </div>
      <section className="comments-section-qualify">
        <StarsQualify
          value={comment.value}
          starsSize={20}
          setValue={handleSetValue}
        />
        <InputText
          handleSubmit={handleAddComment}
          iconComponent={<SendIcon />}
          placeholder={"Agrega un comentario..."}
          id="comment-input"
        />
      </section>
    </section>
  );
};

export default CommentsSection;
