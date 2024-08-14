import { TextField } from "@mui/material";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { Dispatch, MouseEvent, SetStateAction, useState } from "react";

type Props = {
  user: User;
  setUser: Dispatch<SetStateAction<Partial<User>>>;
  setInUser: (param: boolean) => void;
};

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

export default function HamburgerMenu(props: Props) {
  const { setInUser, user, setUser } = props;
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  function handleRoutinesRequired(data: string) {
    //Remove non-numeric characters from the input value
    setUser({ ...user, pointsRequired: Number(data) });
    // updateRequiredPoints(user.id, Number(data));
    localStorage.setItem('requiredPoints', data);

  }

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
      >
        |||
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "right",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          "aria-labelledby": "basic-button",
        }}
      >
        <div className="menu-username">{user.userName} </div>
        <MenuItem onClick={() => setInUser(true)}>Switch User</MenuItem>
        <MenuItem>
          <TextField
            label="Routine Points Required"
            id="outlined-size-small"
            defaultValue={Number(localStorage.getItem('requiredPoints'))}
            size="small"
            onChange={(data) => handleRoutinesRequired(data.target.value)}
            type='number'
            inputProps={{
              inputMode: 'numeric', // Ensures a numeric keyboard is displayed on mobile devices
              pattern: '[0-9]*',    // HTML5 validation pattern to ensure only numeric input
            }}
          />
        </MenuItem>
      </Menu>
    </div>
  );
}
