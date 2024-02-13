import { Checkbox } from "@mui/material";
import { useState } from "react";

export const CheckBox = ({ setPoints, pointValue }: any) => {
  const [isChecked, setIsChecked] = useState(false);
  const handleCheckboxChange = () => {
    if (!isChecked) setPoints((prev: number) => prev + pointValue);
    if (isChecked) setPoints((prev: number) => prev - pointValue);
    setIsChecked(!isChecked);
    console.log("checked");
  };
  return <Checkbox checked={isChecked} onClick={handleCheckboxChange} />;
};
