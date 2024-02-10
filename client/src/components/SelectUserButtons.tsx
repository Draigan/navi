import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import Box from "@mui/material/Box";
import { useMutation, useQueryClient } from "react-query";
import { deleteUser } from "../utils/axiosRequests";

type Props = {
  data: { userName: string; id: string }[];
};

export default function SelectUserButtons(props: Props) {
  const { data } = props;
  const queryClient = useQueryClient();
  const { mutate } = useMutation(deleteUser, {
    onSuccess: () => {
      queryClient.invalidateQueries("usersList");
    },
  });
  const buttons: JSX.Element[] = [];
  console.log(data, "from userButtons");
  data?.forEach((user, index) =>
    buttons.push(
      <>
        <Button onClick={() => mutate(user.id)} key={`${index}`}>
          {user.userName}
        </Button>
      </>,
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
