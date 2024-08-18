import React, { useState, useEffect } from 'react';
import Topbar from '../../common/component/topbar/Topbar';
import Sidenavbar from '../../common/component/sidebar/Sidenavbar';
import FeeOverview
 from './FeeOverview';
export default function FeePage(){
  
  
  
  
  
  return(
    <div className='feepage-container'>
            <Topbar/>        
            <Sidenavbar/>
            <FeeOverview/>
            
    </div>
  )
}