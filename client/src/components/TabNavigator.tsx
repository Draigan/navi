import * as React from "react";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import TabContext from "@mui/lab/TabContext";
import { TabPanel } from "@mui/lab";
import TasksTable from "./TasksTable";
export default function TabNavigator() {
  const [value, setValue] = React.useState("one");

  const handleChange = (_: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  return (
    <Box
      sx={{
        width: "100%",

        "& .MuiTabPanel-root": {
          padding: 0,
        },
      }}
    >
      <TabContext value={value}>
        <Tabs
          variant="fullWidth"
          value={value}
          onChange={handleChange}
          textColor="secondary"
          indicatorColor="secondary"
          aria-label="secondary tabs example"
        >
          <Tab value="one" label="Morning Routine" />
          <Tab value="two" label="Daily Tasks" />
          <Tab value="three" label="Item Three" />
        </Tabs>
        <TabPanel value="one">
          <TasksTable />
        </TabPanel>
        <TabPanel value="two">
          <TasksTable />
        </TabPanel>
        <TabPanel value="three">Item Three</TabPanel>
      </TabContext>
    </Box>
  );
}
