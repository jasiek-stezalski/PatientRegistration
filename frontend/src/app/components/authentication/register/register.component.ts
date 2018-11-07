import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(public userService: UserService, public router: Router) {
  }

  ngOnInit() {
  }

  register() {
    if (this.user.password == this.user.confirmPassword) {
      this.userService.createUser(this.user).subscribe(() => {
          this.router.navigate(['/login']);
        }, err => {
          console.log(err);
          if (err.valueOf().status === 409)
            this.errorMessage = 'Błąd : Taki użytkownik już istnieje';
        }
      );
    } else {
      this.errorMessage = 'Błąd : Hasła muszą być takie same';
    }
  }

}
