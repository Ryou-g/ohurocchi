import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "user_name") val user_Name: String,
    @NonNull @ColumnInfo(name = "money") val money: Int,
    @NonNull @ColumnInfo(name = "apply_face") val apply_Face: Int,
    @NonNull @ColumnInfo(name = "apply_background") val apply_Background: Int,
    @NonNull @ColumnInfo(name = "clean") val clean: Int,
    @NonNull @ColumnInfo(name = "apply_costume") val apply_Costume: Int,
    @NonNull @ColumnInfo(name = "favorability") val favorability : Int,
)