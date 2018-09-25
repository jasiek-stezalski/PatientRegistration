import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {User} from '../../../models/user.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User=new User();
  errorMessage:string;
  constructor(private authService :AuthService, private router: Router) { }



  ngOnInit() {
  }

  login(){
    this.authService.logIn(this.user)
      .subscribe(()=>{
          this.router.navigate(['/profile']);
        },()=>{
          this.errorMessage="error :  Username or password is incorrect";
        }
      )
  }
}
