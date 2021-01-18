import { IProject } from 'app/shared/model/project.model';
import { IUser } from 'app/core/user/user.model';
import { IWeek } from 'app/shared/model/week.model';

export interface IActivity {
  id?: number;
  timeSpent?: number;
  projects?: IProject[];
  users?: IUser[];
  weeks?: IWeek[];
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public timeSpent?: number,
    public projects?: IProject[],
    public users?: IUser[],
    public weeks?: IWeek[]
  ) {}
}
