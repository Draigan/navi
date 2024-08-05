import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import Box from "@mui/material/Box";
import { useMutation, useQueryClient } from "react-query";
import { deleteUser } from "../utils/axiosRequests";
import { Dispatch, SetStateAction } from "react";
import DeleteIcon from "@mui/icons-material/Delete";

type User = {
  userName: string;
  id: string;
  routinesRequired: null | number;
  pointsRequired: null | number;
  choresRequired: null | number;
  routinesChecked: null | number;
  pointsChecked: null | number;
  choresChecked: null | number;
};

type Props = {
  data: { userName: string; id: string }[];
  setInUser: (param: boolean) => void;
  setUser: Dispatch<SetStateAction<Partial<User>>>;
};

export default function SelectUserButtons(props: Props) {
  const { data, setInUser, setUser } = props;
  const queryClient = useQueryClient();
  const { mutate } = useMutation(deleteUser, {
    onSuccess: () => {
      queryClient.invalidateQueries("usersList");
    },
  });

  const handleDelete = (userName: string, userId: string) => {
    const isConfirmed = confirm(
      `Are you sure you would like to delete user "${userName}"?`,
    );
    if (isConfirmed) {
      mutate(userId);
    }
  };
  const buttons: JSX.Element[] = [];
  function handleClickUser(id: string, userName: string) {
    // Set the current user
    setUser((prev) => ({
      ...prev,
      id: id,
      userName: userName,
    }));
    // Ghetto route change
    setInUser(false);
  }

  console.log(data, "from userButtons");
  data?.forEach((user) =>
    buttons.push(
      <div key={Math.random()}>
        <Button
          onClick={() => handleClickUser(user.id, user.userName)}
          key={Math.random()}
          className="usernamebutton"
        >
          {user.userName}
        </Button>
        <Button
          onClick={() => handleDelete(user.userName, user.id)}
          variant="text"
          key={Math.random()}
          startIcon={<DeleteIcon />}
        />
      </div>,
    ),
  );

  return (
    <Box
      sx={{
        display: "flex",
        "& > *": {
          m: 1,
        },
      }}
    >
      <ButtonGroup
        orientation="vertical"
        aria-label="Vertical button group"
        variant="outlined"
      >
        {buttons}
      </ButtonGroup>
    </Box>
  );
}
