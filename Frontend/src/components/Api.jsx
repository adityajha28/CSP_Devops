import React from 'react'
import axios from "axios";

const baseURL = "http://localhost:8081/";

const Api = axios.create({
  // .. congigure axios baseURL
  baseURL: `${baseURL}`
});


export default Api