import { GridRowModel } from "@mui/x-data-grid";
import Axios from "axios";

export async function postNewUser(userName: string) {
  try {
    const response = await Axios.post("http://localhost:9090/users/new", {
      userName: userName,
    });
    console.log("Server:", response.data);
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}

export async function getAllUserNames() {
  try {
    const response = await Axios.get("http://localhost:9090/users/all");
    console.log("Server:", response.data);
    return response.data;
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}

export async function deleteUser(id: string) {
  try {
    const response = await Axios.post("http://localhost:9090/users/delete", {
      id: id,
    });
    console.log("Server:", response.data);
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}

export async function getAllTasksForUser(userId: string) {
  try {
    const response = await Axios.get(
      `http://localhost:9090/tasks/user?user=${userId}`,
    );
    console.log("Server:", response.data);
    return response.data;
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}
export async function updateTask(row: GridRowModel) {
  console.log("Client: row: ", row);
  try {
    const response = await Axios.post(
      "http://localhost:9090/tasks/update",
      row,
    );
    console.log("Server:", response.data);
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}

export async function deleteTask(row: GridRowModel) {
  console.log("Client: row: ", row);
  try {
    const response = await Axios.post(
      "http://localhost:9090/tasks/delete",
      row,
    );
    console.log("Server:", response.data);
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}

export async function swapTask(row: GridRowModel, direction: string) {
  try {
    const response = await Axios.post(
      `http://localhost:9090/tasks/swap?direction=${direction}`,
      row,
    );
    // console.log("Server:", response.data);
    return response.data;
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}

export async function addTask(row: GridRowModel) {
  try {
    const response = await Axios.post("http://localhost:9090/tasks/add", row);
    console.log("Server:", response.data);
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error to be caught by the caller if needed
  }
}
