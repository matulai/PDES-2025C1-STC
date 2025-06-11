import { StarsQualify } from "@/components";
import "@/styles/CommentItem.css";

interface CommentProps {
  text: string;
  value: number;
  userName: string;
}

const Comment = ({ userName, text, value }: CommentProps) => {
  return (
    <div className="comment">
      <p className="comment-title">{userName}</p>
      <StarsQualify value={value} starsSize={20} />
      <p className="comment-text">{text}</p>
    </div>
  );
};

export default Comment;
