import React, { createContext, useContext, useState } from 'react';

const MyContext = createContext();

const MyProvider = ({ children }) => {
    const [value, setValue] = useState('Hello, Korea');
    return (
        <MyContext.Provider value={{ value, setValue }}>
            {children}
        </MyContext.Provider>
    );
};

const MyComponent = () => {
    const { value, setValue } = useContext(MyContext);
    return (
        <div>
            <p>{value}</p>
            <button onClick={() => setValue('Hello, React!')}>Change Value</button>
        </div>
    );
};



export default function Example(){
    
    
    return (
        <div>
            Hello
        </div>
    )
}