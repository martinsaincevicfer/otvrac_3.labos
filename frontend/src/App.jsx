import './App.css'
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Index from "./components/Index.jsx";
import Datatable from "./components/Datatable.jsx";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Navigate to="/index" />} />
              <Route path="/datatable" Component={Datatable}/>
              <Route path="/index" Component={Index}/>
          </Routes>
      </BrowserRouter>
  )
}

export default App
