import logo from '../../logo.svg';
import './App.css';
import Header from "../Header/Header";
import AppContent from "./AppContent/AppContent";

function App() {
  return (
    <div>
      <Header pageTitle="Frontend Authentication with JWT" logoSrc={logo}></Header>
        <div className="container-fluid">
            <div className="row">
                <div className="col">
                    <AppContent />
                </div>
            </div>
        </div>

    </div>
  );
}

export default App;
