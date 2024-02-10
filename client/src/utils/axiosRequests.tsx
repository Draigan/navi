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
